import java.util.Optional;

public class OptionalBasic {

    public OptionalBasic() {

    }

    public void optionalBasic() {
        Optional<String> optional = Optional.empty();

        // optional은 isPesent()-get() 쌍으로 써야 좋다.
        // 방법 1
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }


    }

    // isPresent()-get() 쌍 >> bad
    public String isPresentAndGet() {
        Optional<String> optional = Optional.empty();

        if (optional.isPresent()) {
            return optional.get();
        }
        else {
            return "";
        }
    }

    // orElseGet() >> good
    public String usingOrElseGet() {
        Optional<String> optional = Optional.empty();

        return optional.orElseGet(String::new);
    }

    public String usingOrElseThrow() {
        Optional<String> optional = Optional.empty();

        return optional.orElseThrow(IllegalStateException::new);
    }
}
