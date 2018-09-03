package transformer.com.karan.dev.transform.transformer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KaranDeepSingh
 *
 * @author KaranDeepSingh
 */

public class Transformer implements Parcelable {

    public static final Parcelable.Creator<Transformer> CREATOR = new Parcelable.Creator<Transformer>() {
        public Transformer createFromParcel(Parcel in) {
            return new Transformer(in);
        }

        public Transformer[] newArray(int size) {
            return new Transformer[size];
        }
    };


    private String name;
    private String type;
    private int strength;
    private int intell;
    private int speed;
    private int endu;
    private int rank;
    private int courage;
    private int fp;
    private int skill;
    private int overall;

    public Transformer(Parcel in) {

        name = in.readString();
        type = in.readString();

        if (in.dataAvail() > 0) { // is there data left to read?
            strength = in.readInt();
            intell = in.readInt();
            speed = in.readInt();
            endu = in.readInt();
            rank = in.readInt();
            courage = in.readInt();
            fp = in.readInt();
            skill = in.readInt();
            overall = in.readInt();
        }
    }

    public Transformer(String name, String type, int strength, int intell, int speed, int endu, int rank, int courage, int fp, int skill, int overall) {
        this.name = name;
        this.type = type;
        this.strength = strength;
        this.intell = intell;
        this.speed = speed;
        this.endu = endu;
        this.rank = rank;
        this.courage = courage;
        this.fp = fp;
        this.skill = skill;
        this.overall = overall;
    }

    public Transformer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntell() {
        return intell;
    }

    public void setIntell(int intell) {
        this.intell = intell;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEndu() {
        return endu;
    }

    public void setEndu(int endu) {
        this.endu = endu;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCourage() {
        return courage;
    }

    public void setCourage(int courage) {
        this.courage = courage;
    }

    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {
        this.fp = fp;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getOverall() {
        return overall;
    }

    public void setOverall(int overall) {
        this.overall = overall;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(strength);
        dest.writeInt(intell);
        dest.writeInt(speed);
        dest.writeInt(endu);
        dest.writeInt(rank);
        dest.writeInt(courage);
        dest.writeInt(fp);
        dest.writeInt(skill);
        dest.writeInt(overall);
    }
}
