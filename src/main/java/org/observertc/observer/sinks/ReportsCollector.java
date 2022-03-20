package org.observertc.observer.sinks;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.observertc.observer.reports.Report;
import org.observertc.observer.reports.ReportTypeVisitor;
import org.observertc.observer.reports.ReportTypeVisitors;
import org.observertc.observer.common.PassiveCollector;
import org.observertc.observer.configs.ObserverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Singleton
public class ReportsCollector {

    private static final Logger logger = LoggerFactory.getLogger(ReportsCollector.class);

    @Inject
    ObserverConfig observerConfig;
    private final Subject<List<Report>> reportSubject = PublishSubject.create();
    private PassiveCollector<Report> reportsCollector;
    private ReportTypeVisitor<Void, Boolean> reportsFilter = null;

    @PostConstruct
    void setup() {
        var maxItems = observerConfig.buffers.outboundReportsCollector.maxItems;
        var maxTimeInMs = observerConfig.buffers.outboundReportsCollector.maxTimeInS * 1000;
        this.reportsCollector = PassiveCollector.<Report>builder()
                .withListener(this.reportSubject::onNext)
                .withMaxItems(maxItems)
                .withMaxTimeInMs(maxTimeInMs)
                .build();

        this.reportsFilter = ReportTypeVisitors.makeTypeFilter(observerConfig.reports);
    }

    @PreDestroy
    void teardown() {
        try {
            this.reportsCollector.flush();
        } catch (Exception e) {
            logger.warn("Error occurred", e);
        }
    }

    public void accept(Report report) {
        if (!this.reportsFilter.apply(null, report.type)) {
            return;
        }
        try {
            this.reportsCollector.add(report);
        } catch (Throwable e) {
            logger.warn("Error occurred", e);
        }
    }

    public void acceptAll(List<Report> reports) {
        if (Objects.isNull(reports) || reports.size() < 1) {
            return;
        }
        var filteredReports = reports.stream()
                .filter(report -> this.reportsFilter.apply(null, report.type))
                .collect(Collectors.toList());
        try {
            this.reportsCollector.addAll(filteredReports);
        } catch (Throwable e) {
            logger.warn("Error occurred", e);
        }
    }

    public Observable<List<Report>> getObservableReports() {
        return this.reportSubject;
    }
}
