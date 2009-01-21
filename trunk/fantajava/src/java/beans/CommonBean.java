package beans;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class CommonBean extends HashMap<String, Object>
{
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public CommonBean()
        {

        }

        @SuppressWarnings("unchecked")
        public void populate(HttpServletRequest r)
        {
                //System.out.println("CommonBean.populate()");
                Enumeration<String> e = r.getParameterNames();
                while(e.hasMoreElements())
                {
                        String n = e.nextElement();
                        System.out.println("this.put(" + n + ", " + r.getParameter(n) + ")");
                        this.put( n, r.getParameter(n) );
                }
        }
}
