package com.tag.app.tagnearemployee.qrcode;

import com.tag.app.tagnearemployee.base.BaseModel;
import com.tag.app.tagnearemployee.base.BasePresenter;
import com.tag.app.tagnearemployee.base.BaseView;
import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.QrCodeResponse;

public interface QrCodeContract
{
  public interface View extends BaseView
  {void qrcoderesponse(QrCodeResponse qrCodeResponse);}

  public interface Presenter extends BasePresenter
  {void qrcodeverify(String authorization, String qrCode);}

  public interface Model extends BaseModel
  {void qrcodevalid(String authorization, String qrCode, ModelCallback modelCallback);}
}
