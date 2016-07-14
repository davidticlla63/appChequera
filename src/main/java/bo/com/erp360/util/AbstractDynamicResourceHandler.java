package bo.com.erp360.util;

import java.util.Map;
import javax.faces.application.Application;
import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public abstract class AbstractDynamicResourceHandler
{
  private static final StreamedContent EMPTY_STREAMED_CONTENT = new DefaultStreamedContent();
  
  public StreamedContent getStreamedContentImage()
    throws Exception
  {
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceHandler handler = context.getApplication().getResourceHandler();
    
    Map<String, String> params = context.getExternalContext().getRequestParameterMap();
    String param = (String)params.get("param");
    if (handler.isResourceRequest(context)) {
      return buildStreamedContentImage(context, Integer.valueOf(Integer.parseInt(param)));
    }
    return EMPTY_STREAMED_CONTENT;
  }
  
  protected abstract StreamedContent buildStreamedContentImage(FacesContext paramFacesContext, Integer paramInteger)
    throws Exception;
}
