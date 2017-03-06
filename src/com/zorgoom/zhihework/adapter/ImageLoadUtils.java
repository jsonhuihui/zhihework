package com.zorgoom.zhihework.adapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zorgoom.zhihework.R;

import android.content.Context;
import android.widget.ImageView;

/**
 * 
 * @ClassName: ImageLoadUtils
 *
 */
public class ImageLoadUtils {

	@SuppressWarnings("deprecation")
	public static Object[] initImageLoad(Context mContext) {
		Object[] objects = new Object[2];
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.first_image)
				.showImageForEmptyUri(R.drawable.first_image).showImageOnFail(R.drawable.first_image)
				.cacheInMemory(true).cacheOnDisc(true).build();
		objects[0] = imageLoader;
		objects[1] = options;
		return objects;
	}

	/**
	 * 
	 * @Title: loadImage @throws
	 */
	public static void loadImage(Object[] imageLoadObj, String string, ImageView img) {
		((ImageLoader) imageLoadObj[0]).displayImage(string, img, ((DisplayImageOptions) imageLoadObj[1]));
	}
}
