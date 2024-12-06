package backend.academy;

import backend.academy.fractalFlame.Processor;
import backend.academy.fractalFlame.cmdReader.Reader;
import backend.academy.fractalFlame.transformation.TransformationDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {

        new Reader(args).start();
    }

    //15 сек многопоточка
}
