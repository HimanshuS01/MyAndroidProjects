package com.codingblocks.materialdesignlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class TextViewExpandable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Expandable Text View");
        setContentView(R.layout.activity_text_view_expandable);

        ExpandableTextView expandableTextView = (ExpandableTextView) findViewById(R.id.expand_text_view);
        expandableTextView.setText("Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License.");
    }
}
