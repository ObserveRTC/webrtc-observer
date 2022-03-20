package org.observertc.observer.evaluators.listeners;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.observertc.observer.repositories.RepositoryEvents;
import org.observertc.schemas.reports.CallEventReport;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;

@Singleton
public class CallEvents {

    private Subject<List<CallEventReport>> reports = PublishSubject.create();

    @Inject
    RepositoryEvents repositoryEvents;

    @Inject
    CallStarted callStarted;

    @Inject
    CallEnded callEnded;

    @Inject
    ClientJoined clientJoined;
//
    @Inject
    ClientLeft clientLeft;

    @Inject
    PeerConnectionOpened peerConnectionOpened;

    @Inject
    PeerConnectionClosed peerConnectionClosed;

    @Inject
    MediaTrackAdded mediaTrackAdded;

    @Inject
    MediaTrackRemoved mediaTrackRemoved;

    @PostConstruct
    void setup() {
        this.callStarted.getObservableReports()
                .subscribe(this::forward);

        this.callEnded.getObservableReports()
                .subscribe(this::forward);

        this.clientJoined.getObservableReports()
                .subscribe(this::forward);

        this.clientLeft.getObservableReports()
                .subscribe(this::forward);

        this.peerConnectionOpened.getObservableReports()
                .subscribe(this::forward);

        this.peerConnectionClosed.getObservableReports()
                .subscribe(this::forward);

        this.mediaTrackAdded.getObservableReports()
                .subscribe(this::forward);

        this.mediaTrackRemoved.getObservableReports()
                .subscribe(this::forward);

    }

    @PreDestroy
    void teardown() {

    }

    public Observable<List<CallEventReport>> getObservableCalEventReports() {
        return this.reports;
    }

    private void forward(List<CallEventReport> callEventReports) {
        if (Objects.isNull(callEventReports)) {
            return;
        }
        if (callEventReports.size() < 1) {
            return;
        }
        synchronized (this) {
            this.reports.onNext(callEventReports);
        }
    }

}
