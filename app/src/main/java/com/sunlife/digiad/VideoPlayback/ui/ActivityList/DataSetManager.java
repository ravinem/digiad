package com.sunlife.digiad.VideoPlayback.ui.ActivityList;

/**
 * Created by HKT7 on 7/11/2017.
 */
import android.app.Activity;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DataSetManager {
    public void getDataset(Activity mActivity)
    {
        String baseAddr = "http://arservice.somee.com/data/";
        createXML(mActivity);

    }

    private void createXML(Activity mActivity)
    {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"></soap:Envelope>";

        try
        {
            java.io.FileWriter fw = new java.io.FileWriter(mActivity.getCacheDir() + "/my-file.xml");
            fw.write(xmlString);
            fw.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
