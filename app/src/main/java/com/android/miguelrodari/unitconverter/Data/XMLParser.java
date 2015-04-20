package com.android.miguelrodari.unitconverter.Data;

import android.content.Context;

import com.android.miguelrodari.unitconverter.Constants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 4/14/2015.
 */
public class XMLParser {

    public static List<UnitOfMeasure> loadXML(Context context){
        XmlPullParserFactory xmlPullParserFactory;
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlPullParserFactory.newPullParser();
            InputStream inputStream = context.getAssets().open(Constants.UNITS_OF_MEASURE_XML);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            return parseXML(parser);

        }catch (XmlPullParserException e){
            e.printStackTrace();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private static List<UnitOfMeasure> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException{
        List<UnitOfMeasure> unitOfMeasureList = new ArrayList<>();
        UnitOfMeasure unitOfMeasure = null;
        int eventType = parser.getEventType();
        String name = null;
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    unitOfMeasureList = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("unit")){
                        unitOfMeasure = new UnitOfMeasure();
                    } else if(unitOfMeasure != null){
                        if (name.equals("name")){
                            unitOfMeasure.setName(parser.nextText());
                        }else if(name.equals("category")){
                            unitOfMeasure.setCategory(parser.nextText());
                        }else if(name.equals("standardFormat")){
                            unitOfMeasure.setStandardFormat(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("unit") && unitOfMeasure != null){
                        unitOfMeasureList.add(unitOfMeasure);
                    }
                    break;
            }
            eventType = parser.next();
        }
        return unitOfMeasureList;
    }

}
