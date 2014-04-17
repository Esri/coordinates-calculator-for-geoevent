package com.esri.geoevent.processor.coordinatescalc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.esri.ges.core.component.ComponentException;
import com.esri.ges.core.geoevent.GeoEvent;
import com.esri.ges.core.geoevent.GeoEventDefinition;
import com.esri.ges.core.property.Property;
import com.esri.ges.messaging.GeoEventCreator;
import com.esri.ges.processor.GeoEventProcessorBase;
import com.esri.ges.processor.GeoEventProcessorDefinition;
import com.esri.ges.spatial.Geometry;
import com.esri.ges.spatial.Point;

public class CoordinatesCalcProcessor extends GeoEventProcessorBase
{
	private static final Log LOG = LogFactory.getLog(CoordinatesCalcProcessor.class);

	private long lastReport = 0;
	private int maxMessageRate = 500;
	private boolean printedWarning;
  private GeoEventCreator geoEventCreator;

	protected CoordinatesCalcProcessor(GeoEventProcessorDefinition definition) throws ComponentException
	{
		super(definition);
	}

	@Override
	public GeoEvent process(GeoEvent geoEvent) throws Exception
	{
		if( maxMessageRate > 0 )
		{
			if( (System.currentTimeMillis() - lastReport) > maxMessageRate )
			{
				if(!printedWarning)
				{
				  Geometry geometry = geoEvent.getGeometry();
				  if (geometry != null)
				  {
  				  //String geometryJson = geometry.toJson();
  				  Point point = (Point)geometry;
  				  int wX = (int)Math.floor(point.getX());
            int lonDD = (int)(wX / 100);
  				  int lonMM = (int)(wX % 100); 
            double dX = point.getX() - wX;
  				  double longitude = lonDD + lonMM/60.0 + dX/60.0;
  				  
            int wY = (int)Math.floor(point.getY());
            int latDD = (int)(wY / 100);
            int latMM = (int)(wY % 100); 
            double dY = point.getY() - wY;
            double latitude = latDD + latMM/60.0 + dY/60.0;
  				  
  				  consoleDebugPrintLn(longitude + "," + latitude);
  				  
  				  //GeoEventDefinition edIn = geoEvent.getGeoEventDefinition();
  	        //GeoEvent geoEventOut = geoEventCreator.create(edIn.getGuid(), new Object[] {geoEvent.getAllFields(), new Object[1]});
  				  GeoEvent geoEventOut = (GeoEvent)geoEvent.clone(null);
  	        Point newPoint = (Point)geoEventOut.getGeometry();
  				  newPoint.setX(longitude);
  				  newPoint.setY(latitude);
  				  geoEventOut.setGeometry((Geometry)newPoint);
  				  
  				  newPoint = (Point)geoEvent.getGeometry();
  				  consoleDebugPrintLn(newPoint.getX() + "," + newPoint.getY());
  				  return geoEventOut;
				  }
				  //consoleDebugPrintLn("Coordinates Calculation Processing ... (Limiting output to no more than 1 line every "+maxMessageRate+" ms)");
					//LOG.debug("Coordinates Calculation Processing ... (Limiting output to no more than 1 line every "+maxMessageRate+" ms)");
				}
				else
				{
          consoleDebugPrintLn("Coordinates Calculation Processing ...");
					LOG.debug("Coordinates Calculation Processing ... ");
				}
				lastReport = System.currentTimeMillis();
			}
		}
		else
		{
      consoleDebugPrintLn("Coordinates Calculation Processing ...");
			LOG.debug("Coordinates Calculation Processing ... ");
		}

		return geoEvent;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(definition.getName());
		sb.append("/");
		sb.append(definition.getVersion());
		sb.append("[");
		for (Property p : getProperties())
		{
			sb.append(p.getDefinition().getPropertyName());
			sb.append(":");
			sb.append(p.getValue());
			sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
	
  public static void consoleDebugPrintLn(String msg)
  {
    String consoleOut = System.getenv("GEP_CONSOLE_OUTPUT");
    if (consoleOut != null && "1".equals(consoleOut))
    {
      System.out.println(msg);
      LOG.debug(msg);
    }
  }

  public static void consoleDebugPrint(String msg)
  {
    String consoleOut = System.getenv("GEP_CONSOLE_OUTPUT");
    if (consoleOut != null && "1".equals(consoleOut))
    {
      System.out.print(msg);
      LOG.debug(msg);
    }
  }
	
}