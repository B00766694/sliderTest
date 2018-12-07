package com.olga;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final HorizontalLayout layout = new HorizontalLayout(); //master layout
        VerticalLayout v = new VerticalLayout(); //sub layout 1
        VerticalLayout v2 = new VerticalLayout();//sub layout 2
       
        final TextField value = new TextField();
        value.setCaption("Type value here:");
        value.setPlaceholder("125");
        

        Slider s = new Slider("Value", 1, 250);
        s.setValue(125.0);
        s.setWidth(s.getMax()+"px");
   
        s.addValueChangeListener(e ->{
            double x = s.getValue();
            value.setValue(""+x);
        });

        value.addValueChangeListener(e ->{
            
            double x = Double.parseDouble(value.getValue());
            if (x>s.getMax()){
               s.setMax(x);
               s.setWidth(x+"px");
            }
            else if (x<s.getMin()){
                x = s.getMin();
            }
            s.setValue(x);
        });
        Button button = new Button("Reset");
        button.addClickListener(e -> {
            s.setValue(125.0);
            s.setMax(s.getValue()*2);
            s.setWidth(s.getMax()+"px");
           value.setValue(""+s.getValue());
        });
        Button button2 = new Button("Flip");
        button2.addClickListener(e -> {
            s.setValue(125.0);
            s.setMax(s.getValue()*2);
            s.setWidth(s.getMax()+"px");
            s.setOrientation(SliderOrientation.VERTICAL);
            value.setValue(""+s.getValue());

        });
        v.addComponents(value, s); //build sub layout
        v2.addComponents(button, button2); //build sub layout
        layout.addComponents(v2,v); // build master layout
        
        setContent(layout); //set starting layout point.
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
