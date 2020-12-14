package com.example.imageapp.ui.details

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imageapp.MainActivity
import com.example.imageapp.R
import com.example.imageapp.databinding.FragmentDetailsBinding
import java.io.IOException


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    // loads details fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val photo = args.photo

            Glide.with(this@DetailsFragment)
                .load(photo.urls.full)
                .error(R.drawable.ic_no_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed( // error
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady( // ok
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        textViewDescription.isVisible = photo.description != null
                        likeButton.isVisible = true
                        wallpaperButton.isVisible = true

                        return false
                    }
                })
                .into(imageView)

            // clickable username
            textViewDescription.text = photo.description

            val uri = Uri.parse(photo.links.html)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            (activity as MainActivity).supportActionBar?.title = photo.user.username

            textViewCreator.apply {
                text = "Photo by ${photo.user.name} on Unsplash"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true

            }

            likeButton.setOnClickListener {
                likeButton.setImageResource(R.drawable.ic_like_liked)
            }

            // set image as a wallpaper
            wallpaperButton.setOnClickListener {
                Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show();

                val bmap = imageView.drawable.toBitmap()
                val m = WallpaperManager.getInstance(context)

                try {
                    m.setBitmap(bmap)
                    Toast.makeText(context, "WallPaper set", Toast.LENGTH_SHORT).show();
                } catch (e: IOException) {

                    Toast.makeText(context, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT)
                        .show();
                }
            }
        }
    }
}
