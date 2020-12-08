package cn.network;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.request.base.Request;

import java.io.File;

import okhttp3.Response;

public abstract class FileCallback extends AbsCallback<File> {
    private FileConvert convert;    //文件转换类

    public FileCallback() {
        this(null);
    }

    public FileCallback(String destFileName) {
        this(null, destFileName);
    }

    @Override
    public void onStart(Request<File, ? extends Request> request) {
        super.onStart(request);
        NetworkUtil.setHead(request, 0);
    }

    public FileCallback(String destFileDir, String destFileName) {
        convert = new FileConvert(destFileDir, destFileName);
        convert.setCallback(this);
    }

    @Override
    public File convertResponse(Response response) throws Throwable {
        File file = convert.convertResponse(response);
        response.close();
        return file;
    }
}
