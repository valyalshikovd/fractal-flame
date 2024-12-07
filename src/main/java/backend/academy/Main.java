package backend.academy;

import backend.academy.fractalFlame.cmdReader.Reader;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {

        new Reader(args).start();
    }

    //15 сек многопоточка
}
