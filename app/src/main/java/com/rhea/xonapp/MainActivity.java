package com.rhea.xonapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    TextView textv1, textv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textv1=(TextView)findViewById(R.id.textv1);
        textv2=(TextView)findViewById(R.id.textv2);

    }
    public void parsexml(View view1){
        try{
            InputStream is1 = getAssets().open("Weather.xml");
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
            Document document=documentBuilder.parse(is1);

            NodeList nodeList=document.getElementsByTagName("weather");
            textv1.setText("XML DATA");
            textv1.setText(textv1.getText()+"\n_____________");
            for(int i=0; i<nodeList.getLength();i++)
            {
                Node node= nodeList.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    textv1.setText(textv1.getText()+"\nCity:"+getValue("city",element));
                    textv1.setText(textv1.getText()+"\nLatitude:"+getValue("latitude",element));
                    textv1.setText(textv1.getText()+"\nLongitude:"+getValue("longitude",element));
                    textv1.setText(textv1.getText()+"\nHumidity:"+getValue("humidity",element));
                    textv1.setText(textv1.getText()+"\nTemperature:"+getValue("temperature",element));
                    textv1.setText(textv1.getText()+"\n______________");
                }
            }
            is1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void parsejson(View view2){
        String json;
        StringBuilder stringBuilder=new StringBuilder();
        try{
            InputStream is2=getAssets().open("xml.json");
            stringBuilder.append("JSON DATA");
            stringBuilder.append("\n______________");
            byte buffer[] = new byte[is2.available()];
            is2.read(buffer);
            json=new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject =jsonArray.getJSONObject(i);
                stringBuilder.append("\nCity:").append(jsonObject.getString("city")).append("\n");
                stringBuilder.append("Latitude:").append(jsonObject.getString("latitude")).append("\n");
                stringBuilder.append("Longitude:").append(jsonObject.getString("longitude")).append("\n");
                stringBuilder.append("Humidity:").append(jsonObject.getString("humidity")).append("\n");
                stringBuilder.append("Temperature:").append(jsonObject.getString("temperature")).append("\n");
                stringBuilder.append("_____________");
            }
            textv2.setText(stringBuilder.toString());
            is2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private Object getValue(String tag, Element element1) {
        NodeList nodelist1= element1.getElementsByTagName(tag).item(0).getChildNodes();
        Node node=nodelist1.item(0);
        return node.getNodeValue();
    }
}