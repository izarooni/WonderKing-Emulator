package com.izarooni.wkem.client.meta;

import java.util.Objects;

/**
 * @author izarooni
 */
public class QuestMission {

    public enum Status {
        Complete, InProgress, Available, Unavailable
    }

    private final short ID;
    private Status status;

    public QuestMission(short ID) {
        this.ID = ID;

        status = Status.Unavailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestMission that = (QuestMission) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    public short getID() {
        return ID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
