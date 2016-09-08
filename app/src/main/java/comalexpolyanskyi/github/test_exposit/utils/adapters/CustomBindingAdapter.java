package comalexpolyanskyi.github.test_exposit.utils.adapters;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import comalexpolyanskyi.github.test_exposit.R;

/**
 * Created by Алексей on 23.08.2016.
 */

public class CustomBindingAdapter {
    @BindingAdapter("app:ImageBind")
    public static void bindImage(ImageView imageView, String icon) {
        int imageType;
        if(icon == null){
            imageType = R.drawable.w01d;
        }else{
            switch (icon){
                case "01d": imageType = R.drawable.w01d; break;
                case "02d": case "02n": imageType = R.drawable.w02d; break;
                case "01n": imageType =R.drawable.w01n; break;
                case "03d": case "03n": imageType = R.drawable.w03; break;
                case "04d": case "04n": imageType = R.drawable.w04; break;
                case "09d": case "09n": imageType = R.drawable.w05; break;
                case "10d": case "10n": imageType = R.drawable.w06; break;
                case "11d": case "11n": imageType =R.drawable.w07; break;
                case "13d": case "13n": imageType =R.drawable.w08; break;
                case "50d": case "50n": imageType =R.drawable.w50; break;
                default:
                    imageType = R.drawable.w01d;
            }
        }
        imageView.setImageResource(imageType);
    }
}
