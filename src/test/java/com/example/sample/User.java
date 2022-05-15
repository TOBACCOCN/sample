package com.example.sample;

import lombok.Data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Data
public class User implements Externalizable {
    private static String username = "hello";
    private transient String password;
    private String nickname;
    private int chineseScore;
    private int mathScore;
    private int englishScore;
    private int integrationScore;
    private int totalScore;

    public User() {
    }

    public User(int chineseScore, int mathScore, int englishScore, int integrationScore, int totalScore) {
        this.chineseScore = chineseScore;
        this.mathScore = mathScore;
        this.englishScore = englishScore;
        this.integrationScore = integrationScore;
        this.totalScore = totalScore;
    }

    public int getChineseScore() {
        return chineseScore;
    }

    public void setChineseScore(int chineseScore) {
        this.chineseScore = chineseScore;
    }

    public int getMathScore() {
        return mathScore;
    }

    public void setMathScore(int mathScore) {
        this.mathScore = mathScore;
    }

    public int getEnglishScore() {
        return englishScore;
    }

    public void setEnglishScore(int englishScore) {
        this.englishScore = englishScore;
    }

    public int getIntegrationScore() {
        return integrationScore;
    }

    public void setIntegrationScore(int integrationScore) {
        this.integrationScore = integrationScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(username);
        out.writeObject(password);
        out.writeObject(nickname);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        username = (String) in.readObject();
        password = (String) in.readObject();
        nickname = (String) in.readObject();
    }

}