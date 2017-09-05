/*
  Copyright 2017 Esri

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.​

  For additional information, contact:
  Environmental Systems Research Institute, Inc.
  Attn: Contracts Dept
  380 New York Street
  Redlands, California, USA 92373

  email: contracts@esri.com
*/

package com.esri.geoevent.processor.coordinatescalc;

import com.esri.core.geometry.Geometry.Type;
import com.esri.core.geometry.MapGeometry;
import com.esri.core.geometry.Point;
import com.esri.ges.core.component.ComponentException;
import com.esri.ges.core.geoevent.GeoEvent;
import com.esri.ges.core.property.Property;
import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;
import com.esri.ges.processor.GeoEventProcessorBase;
import com.esri.ges.processor.GeoEventProcessorDefinition;

public class CoordinatesCalcProcessor extends GeoEventProcessorBase
{
	private static final BundleLogger	LOGGER	= BundleLoggerFactory.getLogger(CoordinatesCalcProcessor.class);

	private long lastReport = 0;
	private int maxMessageRate = 500;
	private boolean printedWarning;

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
				  MapGeometry geometry = geoEvent.getGeometry();
				  if (geometry != null)
				  {
				  	//check if we have point geometry
				  	if( geometry.getGeometry().getType() != Type.Point)
				  	{
				  		LOGGER.debug("NOT_A_POINT_MSG", geoEvent.toString());
				  		return geoEvent;
				  	}
				  	
  				  Point point = (Point) geometry.getGeometry();
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
  				  
  				  MapGeometry clonedGeometry = geoEventOut.getGeometry();
  	        Point newPoint = (Point)clonedGeometry.getGeometry();
  				  newPoint.setX(longitude);
  				  newPoint.setY(latitude);
  				  clonedGeometry.setGeometry(newPoint);
  				  geoEventOut.setGeometry(clonedGeometry);
  				  
  				  consoleDebugPrintLn(point.getX() + "," + point.getY());
  				  return geoEventOut;
				  }
				  //consoleDebugPrintLn("Coordinates Calculation Processing ... (Limiting output to no more than 1 line every "+maxMessageRate+" ms)");
					//LOG.debug("Coordinates Calculation Processing ... (Limiting output to no more than 1 line every "+maxMessageRate+" ms)");
				}
				else
				{
          consoleDebugPrintLn("Coordinates Calculation Processing ...");
					LOGGER.debug("Coordinates Calculation Processing ... ");
				}
				lastReport = System.currentTimeMillis();
			}
		}
		else
		{
      consoleDebugPrintLn("Coordinates Calculation Processing ...");
      LOGGER.debug("Coordinates Calculation Processing ... ");
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
      LOGGER.debug(msg);
    }
  }

  public static void consoleDebugPrint(String msg)
  {
    String consoleOut = System.getenv("GEP_CONSOLE_OUTPUT");
    if (consoleOut != null && "1".equals(consoleOut))
    {
      System.out.print(msg);
      LOGGER.debug(msg);
    }
  }
	
}