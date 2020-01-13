package com.tag.app.tagnearemployee.qrcode;

import com.tag.app.tagnearemployee.base.ModelCallback;
import com.tag.app.tagnearemployee.pojomodels.QrCodeResponse;

public class QrCodePresenter implements QrCodeContract.Presenter
{
    private final QrCodeModel qrCodeModel;
    private QrCodeContract.View view;

    public QrCodePresenter(QrCodeModel qrCodeModel) {
        this.qrCodeModel=qrCodeModel; }

    @Override
    public void setView(Object view)
    { this.view= (QrCodeContract.View) view; }

    @Override
    public void clearView()
    { qrCodeModel.destroy(); }

    @Override
    public void qrcodeverify(String authorization,String qrCode) {
      qrCodeModel.qrcodevalid( authorization, qrCode, new ModelCallback() {
          @Override
          public void onSuccess(Object object) {
              view.qrcoderesponse( (QrCodeResponse) object );
          }

          @Override
          public void onFailure(Throwable throwable) {
             view.onFailure( throwable );
          }
      } );
    }
}
