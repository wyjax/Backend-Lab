import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Optional<String> optional = Optional.empty();

        System.out.println(optional.isPresent());
    }


}