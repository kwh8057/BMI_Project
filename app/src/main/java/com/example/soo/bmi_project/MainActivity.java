package com.example.woooo.bmi_project;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;

        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;
        import com.handstudio.android.hzgrapherlib.animation.GraphAnimation;
        import com.handstudio.android.hzgrapherlib.graphview.CurveGraphView;
        import com.handstudio.android.hzgrapherlib.vo.GraphNameBox;
        import com.handstudio.android.hzgrapherlib.vo.curvegraph.CurveGraph;
        import com.handstudio.android.hzgrapherlib.vo.curvegraph.CurveGraphVO;

        import java.lang.reflect.Type;
        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ViewGroup layoutGraphView;

    String str_height;
    String str_weight;
    String date;
    String day;
    float bmi;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<BMI_Data> item;
    ArrayList<Graph_Data> item_graph;

    SharedPreferences prefsList;
    Type listOfList;
    String strList;

    SharedPreferences prefsGraph;
    Type listOfGraph;
    String strGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutGraphView = (ViewGroup)findViewById(R.id.rv_graph);

        prefsList = PreferenceManager.getDefaultSharedPreferences(MyApplication.getsContext());
        listOfList = new TypeToken<List<BMI_Data>>(){}.getType();
        item = new Gson().fromJson(prefsList.getString("LIST", ""), listOfList);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MyApplication.getsContext());
        recyclerView.setLayoutManager(layoutManager);

        try{
            if(item == null){
                item = new ArrayList<>();
            }else{
                adapter = new BMI_Adapter(MyApplication.getsContext(), item);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        prefsGraph = PreferenceManager.getDefaultSharedPreferences(MyApplication.getsContext());
        listOfGraph = new TypeToken<List<Graph_Data>>(){}.getType();
        item_graph = new Gson().fromJson(prefsGraph.getString("GRAPH", ""), listOfGraph);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, AddBMIActivity.class);
                startActivityForResult(it, 100);
            }
        });

        //layoutGraphView.invalidate();
        if(item_graph == null){
            item_graph = new ArrayList<>();
        }else{
            //Toast.makeText(MyApplication.getsContext(), "1", Toast.LENGTH_SHORT).show();
            setCurveGraph();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 100){
            str_height = data.getStringExtra("str_height");
            str_weight = data.getStringExtra("str_weight");
            date = data.getStringExtra("date");
            day = data.getStringExtra("day");
            bmi = Float.parseFloat(str_weight) / ((Float.parseFloat(str_height) / 100) * (Float.parseFloat(str_height) / 100));
            //Toast.makeText(MyApplication.getsContext(), str_height + "\n" + str_weight + "\n" + date + "\n" + day, Toast.LENGTH_SHORT).show();

            item.add(new BMI_Data(str_height, str_weight, date, day));

            prefsList = PreferenceManager.getDefaultSharedPreferences(MyApplication.getsContext());
            listOfList = new TypeToken<List<BMI_Data>>(){}.getType();
            strList = new Gson().toJson(item, listOfList);
            prefsList.edit().putString("LIST", strList).apply();

            item = new Gson().fromJson(prefsList.getString("LIST", ""), listOfList);

            adapter = new BMI_Adapter(MyApplication.getsContext(), item);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            item_graph.add(new Graph_Data(date, bmi));

            prefsGraph = PreferenceManager.getDefaultSharedPreferences(MyApplication.getsContext());
            listOfGraph = new TypeToken<List<Graph_Data>>(){}.getType();
            strGraph = new Gson().toJson(item_graph, listOfGraph);
            prefsGraph.edit().putString("GRAPH", strGraph).apply();
            prefsGraph.edit().commit();

            /*String str = "";
            for(int i = 0; i < item_graph.size(); i++){
                str += item_graph.get(i).getDate() + "/" + item_graph.get(i).getBmi() + "\n";
            }
            Toast.makeText(MyApplication.getsContext(), str, Toast.LENGTH_SHORT).show();*/
            //Toast.makeText(MyApplication.getsContext(), "2", Toast.LENGTH_SHORT).show();
            layoutGraphView.removeAllViews();
            setCurveGraph();
        }

    }

    private void setCurveGraph() {
        //all setting
        CurveGraphVO vo = makeCurveGraphAllSetting();

        //default setting
        //CurveGraphVO vo = makeCurveGraphDefaultSetting();
        layoutGraphView = (ViewGroup)findViewById(R.id.rv_graph);
        layoutGraphView.addView(new CurveGraphView(this, vo));
        //Toast.makeText(MyApplication.getsContext(), "3", Toast.LENGTH_SHORT).show();
        // TODO
    }

    /**
     * make simple Curve graph
     * @return
     */
    private CurveGraphVO makeCurveGraphDefaultSetting() {
        String[] legendArr 	= {"1","2","3","4","5"}; // x축
        float[] graph1 		= {500,100,300,200,100}; // y축

        List<CurveGraph> arrGraph = new ArrayList<CurveGraph>();
        arrGraph.add(new CurveGraph("android", 0xaa66ff33, graph1));

        CurveGraphVO vo = new CurveGraphVO(legendArr, arrGraph);
        return vo;
    }

    /**
     * make Curve graph using options
     * @return
     */
    private CurveGraphVO makeCurveGraphAllSetting() {
        //BASIC LAYOUT SETTING
        //padding
        int paddingBottom 	= CurveGraphVO.DEFAULT_PADDING;
        int paddingTop 		= CurveGraphVO.DEFAULT_PADDING;
        int paddingLeft 	= CurveGraphVO.DEFAULT_PADDING;
        int paddingRight 	= CurveGraphVO.DEFAULT_PADDING;

        //graph margin
        int marginTop 		= CurveGraphVO.DEFAULT_MARGIN_TOP;
        int marginRight 	= CurveGraphVO.DEFAULT_MARGIN_RIGHT;

        //max value (x축 최댓값)
        int maxValue 		= 50;

        //increment (x축 증가량)
        int increment 		= 5;

        //GRAPH SETTINGs
        /*String[] legendArr 	= {"1","2","3","4","5"}; // x축
        float[] graph1 		= {500,100,300,200,100}; // y축*/
        prefsGraph = PreferenceManager.getDefaultSharedPreferences(MyApplication.getsContext());
        listOfGraph = new TypeToken<List<Graph_Data>>(){}.getType();
        item_graph = new Gson().fromJson(prefsGraph.getString("GRAPH", ""), listOfGraph);

        String[] legendArr 	= new String[item_graph.size()]; // x축
        float[] graph1 		= new float[item_graph.size()]; // y축

        Log.e("TAG", item_graph.size() + "");

        for(int i = 0; i < item_graph.size(); i++){
            legendArr[i] = item_graph.get(i).getDate();
            //legendArr[i] = (i + 1) + "";
            graph1[i] = (int)item_graph.get(i).getBmi();
        }

        List<CurveGraph> arrGraph 		= new ArrayList<CurveGraph>();

        arrGraph.add(new CurveGraph("BMI", 0xaa66ff33, graph1));

        CurveGraphVO vo = new CurveGraphVO(
                paddingBottom, paddingTop, paddingLeft, paddingRight,
                marginTop, marginRight, maxValue, increment, legendArr, arrGraph);

        //set animation
        vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, GraphAnimation.DEFAULT_DURATION));
        //set graph name box
        vo.setGraphNameBox(new GraphNameBox());
        //set draw graph region
//		vo.setDrawRegion(true);

        //use icon
//		arrGraph.add(new Graph(0xaa66ff33, graph1, R.drawable.icon1));
//		arrGraph.add(new Graph(0xaa00ffff, graph2, R.drawable.icon2));
//		arrGraph.add(new Graph(0xaaff0066, graph3, R.drawable.icon3));

//		CurveGraphVO vo = new CurveGraphVO(
//				paddingBottom, paddingTop, paddingLeft, paddingRight,
//				marginTop, marginRight, maxValue, increment, legendArr, arrGraph, R.drawable.bg);
        return vo;
    }
}