package org.example.weatherBot.telegram.exeptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.weatherBot.telegram.services.MessageType;
@AllArgsConstructor
@Getter
public class UnsuitableResponseException extends Exception{
    MessageType type;
}
