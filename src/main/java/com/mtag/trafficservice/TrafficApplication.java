/*
 * The MIT License
 *
 * Copyright 2015 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mtag.trafficservice;

import com.mtag.trafficservice.model.BufferedTrafficService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author cwahlmann
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class TrafficApplication {
    
    @Bean
    public TrafficService trafficService()
    {
        return new TrafficService();
    }
    
    @Bean
    public BufferedTrafficService bufferedTrafficService()
    {
        return new BufferedTrafficService();
    }

    public static void main(String[] args) {
        for (String arg:args)
        {
            String a=arg.toLowerCase();
            if (a.startsWith("--port="))
            {
                Integer port = new Integer(a.substring(7));
                if (port!=null)
                {
                    ServerCustomizationBean.port=port;
                }
            }
            else
            {
                System.out.println("--- unknown argument '"+arg+"' ---");                
                System.out.println();
                System.out.println("options are:");
                System.out.println("--port=n     change http-port to n (default is 8080)");
                System.out.println();
            }
        }
//        new AnnotationConfigApplicationContext(Tra)
        SpringApplication.run(TrafficApplication.class, args);
    }    
}
