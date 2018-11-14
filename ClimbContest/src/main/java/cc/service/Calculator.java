package cc.service;

import java.util.List;

public interface Calculator {

	String addAttempt(String score, String effect);

	String sumScores(List<String> scores);
}
