package study.coco;
import study.coco.game.Game;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import java.io.IOException;

public class Main {

    private static Terminal terminal;
    private static LineReader reader;

    static Game game;

    public static void main(String[] args) throws IOException {

        terminal = TerminalBuilder.builder().jansi(true).system(true).build();
        reader = LineReaderBuilder.builder().terminal(terminal).history(new DefaultHistory()).build();

        String input;
        String output = "";
        game = new Game();
        game.intro();
        do {
            System.out.print("> ");
            input = reader.readLine();
            output = game.run(input);
            System.out.println(output);
        } while (!"q".equalsIgnoreCase(input));
    }

}
