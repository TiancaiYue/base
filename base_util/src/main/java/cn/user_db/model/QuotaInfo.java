package cn.user_db.model;

/**
 * Mola Android
 * Created by saye on 16/6/27.
 */
public class QuotaInfo extends MolaModel {
    private long total;
    private long used;
    private long personal;
    private long join;
    private long team;

    public QuotaInfo() {
        super();
    }

    public QuotaInfo(long total, long used, long personal, long join, long team) {
        super();
        this.total = total;
        this.used = used;
        this.personal = personal;
        this.join = join;
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof QuotaInfo) {
            QuotaInfo other = (QuotaInfo) o;
            return other.used == used && other.total == total;
        }
        return false;
    }

    public long getJoin() {
        return join;
    }

    public void setJoin(long join) {
        this.join = join;
    }

    public long getPersonal() {
        return personal;
    }

    public void setPersonal(long personal) {
        this.personal = personal;
    }

    public long getTeam() {
        return team;
    }

    public void setTeam(long team) {
        this.team = team;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "QuotaInfo{" +
                "total=" + total +
                ", used=" + used +
                ", personal=" + personal +
                ", join=" + join +
                ", team=" + team +
                '}';
    }
}
