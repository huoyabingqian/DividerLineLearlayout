package dividerlinelinearlayout.example.com.dividerlinelearlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private DividerLinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = ((DividerLinearLayout) findViewById(R.id.dividerLine));
        mLinearLayout.setDividerColor(Color.BLACK);
        mLinearLayout.setDividerWidth(2);
        mLinearLayout.setFooterPadding(30,30);
        mLinearLayout.showFooterDivider(true);
    }
}
