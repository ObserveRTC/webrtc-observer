/*
 * This file is generated by jOOQ.
 */
package com.observertc.gatekeeper.jooq.tables;


import com.observertc.gatekeeper.jooq.Keys;
import com.observertc.gatekeeper.jooq.Observertc;
import com.observertc.gatekeeper.jooq.tables.records.ObserversRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * Observers
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Observers extends TableImpl<ObserversRecord> {

    private static final long serialVersionUID = -346985199;

    /**
     * The reference instance of <code>ObserveRTC.Observers</code>
     */
    public static final Observers OBSERVERS = new Observers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ObserversRecord> getRecordType() {
        return ObserversRecord.class;
    }

    /**
     * The column <code>ObserveRTC.Observers.id</code>. The identifier of the observer for inside relations, never outside
     */
    public final TableField<ObserversRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "The identifier of the observer for inside relations, never outside");

    /**
     * The column <code>ObserveRTC.Observers.uuid</code>. The UUID of the observer published outside 
     */
    public final TableField<ObserversRecord, byte[]> UUID = createField(DSL.name("uuid"), org.jooq.impl.SQLDataType.BINARY(16), this, "The UUID of the observer published outside ");

    /**
     * The column <code>ObserveRTC.Observers.name</code>. The name of the obersver
     */
    public final TableField<ObserversRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(255), this, "The name of the obersver");

    /**
     * The column <code>ObserveRTC.Observers.description</code>. The description for the observer
     */
    public final TableField<ObserversRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(255), this, "The description for the observer");

    /**
     * Create a <code>ObserveRTC.Observers</code> table reference
     */
    public Observers() {
        this(DSL.name("Observers"), null);
    }

    /**
     * Create an aliased <code>ObserveRTC.Observers</code> table reference
     */
    public Observers(String alias) {
        this(DSL.name(alias), OBSERVERS);
    }

    /**
     * Create an aliased <code>ObserveRTC.Observers</code> table reference
     */
    public Observers(Name alias) {
        this(alias, OBSERVERS);
    }

    private Observers(Name alias, Table<ObserversRecord> aliased) {
        this(alias, aliased, null);
    }

    private Observers(Name alias, Table<ObserversRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Observers"), TableOptions.table());
    }

    public <O extends Record> Observers(Table<O> child, ForeignKey<O, ObserversRecord> key) {
        super(child, key, OBSERVERS);
    }

    @Override
    public Schema getSchema() {
        return Observertc.OBSERVERTC;
    }

    @Override
    public Identity<ObserversRecord, Integer> getIdentity() {
        return Keys.IDENTITY_OBSERVERS;
    }

    @Override
    public UniqueKey<ObserversRecord> getPrimaryKey() {
        return Keys.KEY_OBSERVERS_PRIMARY;
    }

    @Override
    public List<UniqueKey<ObserversRecord>> getKeys() {
        return Arrays.<UniqueKey<ObserversRecord>>asList(Keys.KEY_OBSERVERS_PRIMARY, Keys.KEY_OBSERVERS_UUID);
    }

    @Override
    public Observers as(String alias) {
        return new Observers(DSL.name(alias), this);
    }

    @Override
    public Observers as(Name alias) {
        return new Observers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Observers rename(String name) {
        return new Observers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Observers rename(Name name) {
        return new Observers(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, byte[], String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
