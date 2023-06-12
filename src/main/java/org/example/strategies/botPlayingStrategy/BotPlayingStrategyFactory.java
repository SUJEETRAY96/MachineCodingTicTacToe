package org.example.strategies.botPlayingStrategy;

import org.example.models.BotDifficultyLevel;

public class BotPlayingStrategyFactory {


    public static BotPlayingStrategy getBotPlayerStrategyDifficultyLevel(BotDifficultyLevel difficultyLevel){
        return new EasyBotPlayingStrategy();
      /*  return switch (difficultyLevel){
            case EASY -> new EasyBotPlayingStrategy();
            case MEDIUM -> new MediumBotPlayingStrategy();
            case HARD -> new HardBotPlayingStrategy();
        };*/
    }
}
