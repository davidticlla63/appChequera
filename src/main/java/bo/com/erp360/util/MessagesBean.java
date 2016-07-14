package bo.com.erp360.util;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="messagesBean")
@RequestScoped
public class MessagesBean
  extends AbstractManagedBean
  implements Serializable
{
  private static final long serialVersionUID = 8386076001608276180L;
  
  public boolean isError()
  {
    return !getCurrentContext().getMessageList().isEmpty();
  }
}
