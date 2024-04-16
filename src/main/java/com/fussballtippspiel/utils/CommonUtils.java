package com.fussballtippspiel.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fussballtippspiel.beans.GameScore;

public class CommonUtils {
	//method to check if game need to be update
	public static boolean isNeedToUpdateGameAcivity(LocalDateTime kickoffTime) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		System.out.println("Current Time : " + currentDateTime );
		Duration duration = Duration.between(kickoffTime,currentDateTime);
		System.out.println("Duration Time : " + duration );
	    long minutes = duration.toMinutes();
		System.out.println("Duration in minutes : " + minutes );
		System.out.println("Comparison between current and kickoff : " + currentDateTime.compareTo(kickoffTime) );
		if(currentDateTime.compareTo(kickoffTime)>0) {
			return true;
		}
		return false;
	}
	
	//calculate time in minutes
	public static int timeInMinutes(LocalDateTime kickoffTime) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		if(currentDateTime.compareTo(kickoffTime)>=0) {
			return 0;
		}
	    return -1;
	}
	
	//return duration between game kickoff and current time
	public static long isGameTimeOver(LocalDateTime kickoffTime) {
		LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(kickoffTime,currentDateTime);
        return duration.toMinutes();
	}
	
	//calculate points for users
	public static int calculatePoints(GameScore predictedScore ,GameScore actualGameScore ) {
		
		Integer predictedTeam1Score= predictedScore.getTeam_1_score();
		Integer predictedTeam2Score= predictedScore.getTeam_2_score();
		Integer actualGameTeam1Score =  actualGameScore.getTeam_1_score();
		Integer actualGameTeam2Score =  actualGameScore.getTeam_2_score();
		Integer predictedScoreTendency = predictedTeam1Score.compareTo(predictedTeam2Score);
		Integer actualScoreTendency = actualGameTeam1Score.compareTo(actualGameTeam2Score);
		if(predictedTeam1Score.equals(actualGameTeam1Score) && predictedTeam2Score.equals(actualGameTeam2Score)) {
			return 3;
		}else if(predictedScoreTendency == actualScoreTendency) {
			return 1;
		}
		return 0;
	}
}
