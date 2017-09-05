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

import com.esri.ges.processor.GeoEventProcessorDefinitionBase;

public class CoordinatesCalcProcessorDefinition extends GeoEventProcessorDefinitionBase
{
	public CoordinatesCalcProcessorDefinition()
	{
		;
	}
	
	@Override
	public String getName()
	{
		return "CoordinatesCalculationProcessor";
	}
	
	@Override
  public String getDomain()
  {
    return "com.esri.geoevent.processor";
  }
	
	@Override
  public String getVersion()
  {
    return "10.3.0";
  }
	
	@Override
  public String getLabel()
  {
    return "${com.esri.geoevent.processor.coordinates-calc-processor.PROCESSOR_LABEL}";
  }
	
	@Override
  public String getDescription()
  {
    return "${com.esri.geoevent.processor.coordinates-calc-processor.PROCESSOR_DESC}";
  }

  @Override
  public String getContactInfo()
  {
    return "geoeventprocessor@esri.com";
  }
}