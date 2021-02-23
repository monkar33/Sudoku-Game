package view;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {

    private static final Object[][] content = {
            {"1", "Autorki:"},
            {"a1", "Monika Karpi\u0144ska"},
            {"a2", "Karolina \u0141yskowicz"}
    };

    @Override
    protected Object[][] getContents() {
        return content;
    }
}
