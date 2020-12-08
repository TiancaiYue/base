package cn.widget;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;

import cn.base.base_util.R;
import cn.utils.YZGlideUtil;
import cn.utils.YZStringUtil;

public class YZGridImageAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {

    public YZGridImageAdapter() {
        super(R.layout.item_choose_image);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void convert(BaseViewHolder holder, LocalMedia item) {
        ImageView mImg = holder.getView(R.id.iv_image);
        ImageView mIvDel = holder.getView(R.id.iv_del);
        TextView tvDuration = holder.getView(R.id.tv_duration);
        holder.addOnClickListener(R.id.iv_del);

        if (holder.getAdapterPosition() == 0 && YZStringUtil.isEmpty(item.getPath())) {
            mIvDel.setVisibility(View.GONE);
            YZGlideUtil.loadAnyImage(mContext, "", mImg, R.mipmap.addiamge_icon, R.mipmap.addiamge_icon);
        } else {
            mIvDel.setVisibility(View.VISIBLE);
            int chooseModel = item.getChooseModel();
            String path;
            if (item.isCut() && !item.isCompressed()) {
                // 裁剪过
                path = item.getCutPath();
            } else if (item.isCompressed() || (item.isCut() && item.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = item.getCompressPath();
            } else {
                // 原图
                path = item.getPath();
            }

            long duration = item.getDuration();
            tvDuration.setVisibility(PictureMimeType.isHasVideo(item.getMimeType()) ? View.VISIBLE : View.GONE);
            if (chooseModel == PictureMimeType.ofAudio()) {
                tvDuration.setVisibility(View.VISIBLE);
                tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.picture_icon_audio, 0, 0, 0);
            } else {
                tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.picture_icon_video, 0, 0, 0);
            }
            tvDuration.setText(DateUtils.formatDurationTime(duration));
            YZGlideUtil.loadAnyImage(mContext, path, mImg, R.mipmap.addiamge_icon, R.mipmap.addiamge_icon);
        }
    }
}
