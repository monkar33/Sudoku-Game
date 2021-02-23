package view;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {

    private static final Object[][] content = {
            {"1", "Autor:"},
            {"a1", "Monika Karpi\u0144ska"},
    };

    @Override
    protected Object[][] getContents() {
        return content;
    }
}
