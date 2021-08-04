package com.example.ethinkosorganismosemboliasmou.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ethinkosorganismosemboliasmou.R;

import org.w3c.dom.Text;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        // The below code will fix the column number for the grid of the images
        final GridLayout gridLayout = root.findViewById(R.id.my_image_grid);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        // float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Log.d("MyHeight", dpHeight+"");
        // Log.d("MyWidth", dpWidth+"");
        gridLayout.setColumnCount((int)(dpWidth/120));

        // The below code is used to attach videos inside the Fragment
        WebView myWebView = (WebView) root.findViewById(R.id.webview_video_1);
        myWebView.getSettings().setJavaScriptEnabled(true);
        String htmlContent = "<html><body>" +
                "<iframe width=\"auto\" height=\"auto\" src=\"https://www.youtube.com/embed/FC4soCjxSOQ\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>" +
                "</body></html>";
        myWebView.loadData(htmlContent, "text/html", null);

        myWebView = (WebView) root.findViewById(R.id.webview_video_2);
        myWebView.getSettings().setJavaScriptEnabled(true);
        htmlContent = "<html><body>" +
                "<iframe width=\"auto\" height=\"auto\" src=\"https://www.youtube.com/embed/nEzJ_QKjT14\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>" +
                "</body></html>";
        myWebView.loadData(htmlContent, "text/html", null);

        myWebView = (WebView) root.findViewById(R.id.webview_video_3);
        myWebView.getSettings().setJavaScriptEnabled(true);
        htmlContent = "<html><body>" +
                "<iframe width=\"auto\" height=\"auto\" src=\"https://www.youtube.com/embed/6XdjmB4IY3M\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>" +
                "</body></html>";
        myWebView.loadData(htmlContent, "text/html", null);

        myWebView = (WebView) root.findViewById(R.id.webview_video_4);
        myWebView.getSettings().setJavaScriptEnabled(true);
        htmlContent = "<html><body>" +
                "<iframe width=\"auto\" height=\"auto\" src=\"https://www.youtube.com/embed/AnfeQ1avCys\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>" +
                "</body></html>";
        myWebView.loadData(htmlContent, "text/html", null);

        // My images array
        ImageView[] images = {
                (ImageView) root.findViewById(R.id.my_image_1),
                (ImageView) root.findViewById(R.id.my_image_2),
                (ImageView) root.findViewById(R.id.my_image_3),
                (ImageView) root.findViewById(R.id.my_image_4),
                (ImageView) root.findViewById(R.id.my_image_5),
                (ImageView) root.findViewById(R.id.my_image_6)
        };

        // Listeners to make images able to have fullscreen mode
        for(int i=0; i<images.length; i++){
            ImageView imageView = images[i];
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView imagesTitle = root.findViewById(R.id.images_title);
                    TextView videosTitle = root.findViewById(R.id.videos_title);
                    ScrollView scrollView1 = root.findViewById(R.id.scroll_view_gallery_1);
                    ScrollView scrollView2 = root.findViewById(R.id.scroll_view_gallery_2);
                    imagesTitle.setVisibility(View.GONE);
                    videosTitle.setVisibility(View.GONE);
                    scrollView1.setVisibility(View.GONE);
                    scrollView2.setVisibility(View.GONE);

                    ImageView fullScreenImage = root.findViewById(R.id.fullescreen_image);
                    fullScreenImage.setImageDrawable(imageView.getDrawable());
                    fullScreenImage.setVisibility(View.VISIBLE);
                }
            });
        }

        // Listener to make content normal again after fullscreen mode
        ImageView fullScreenImage = (ImageView) root.findViewById(R.id.fullescreen_image);
        fullScreenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreenImage.setVisibility(View.GONE);

                TextView imagesTitle = root.findViewById(R.id.images_title);
                TextView videosTitle = root.findViewById(R.id.videos_title);
                ScrollView scrollView1 = root.findViewById(R.id.scroll_view_gallery_1);
                ScrollView scrollView2 = root.findViewById(R.id.scroll_view_gallery_2);
                imagesTitle.setVisibility(View.VISIBLE);
                videosTitle.setVisibility(View.VISIBLE);
                scrollView1.setVisibility(View.VISIBLE);
                scrollView2.setVisibility(View.VISIBLE);
            }
        });

        /*
        final Button button = root.findViewById(R.id.button_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                button.setText(s);
            }
        });
        */
        return root;
    }
}