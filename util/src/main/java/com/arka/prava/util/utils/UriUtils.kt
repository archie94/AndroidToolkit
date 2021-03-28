package com.arka.prava.util.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import timber.log.Timber

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 13/10/19.
 */
/* Get uri related content real local file path. */
fun Uri.getUriRealPath(ctx: Context): String? {
    // Android OS above sdk version 19.
    return this.getUriRealPathAboveKitkat(ctx)
}

private fun Uri.getUriRealPathAboveKitkat(ctx: Context): String? {
    val resolver = ctx.contentResolver

    // Get uri authority.
    val uriAuthority = this.authority ?: kotlin.run {
        Timber.e("Uri authority is null")
        return null
    }

    val ret: String? = if (this.isContentUri()) {
        if (isGooglePhotoDoc(uriAuthority)) {
            this.lastPathSegment
        } else {
            if (isMediaDoc(uriAuthority)) {
                getPathForMediaDocument(resolver)
            } else {
                this.getImageRealPath(resolver, null)
            }
        }
    } else if (this.isFileUri()) {
        this.path
    } else if (this.isDocumentUri(ctx)) {

        // Get uri related document id.
        val documentId = DocumentsContract.getDocumentId(this)

        if (isMediaDoc(uriAuthority)) {
            getPathForMediaDocument(resolver)
        } else if (isDownloadDoc(uriAuthority)) {
            // Build download uri.
            val downloadUri = Uri.parse("content://downloads/public_downloads")

            // Append download document id at uri end.
            val downloadUriAppendId =
                ContentUris.withAppendedId(downloadUri, java.lang.Long.valueOf(documentId))

            downloadUriAppendId.getImageRealPath(resolver, null)
        } else if (isExternalStoreDoc(uriAuthority)) {
            val idArr =
                documentId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (idArr.size == 2) {
                val type = idArr[0]
                val realDocId = idArr[1]

                if ("primary".equals(type, ignoreCase = true)) {
                    Environment.getExternalStorageDirectory().absolutePath + "/" + realDocId
                } else {
                    null
                }
            } else {
                null
            }
        } else {
            null
        }
    } else {
        null
    }

    return ret
}

/* Check whether this uri represent a document or not. */
private fun Uri.isDocumentUri(ctx: Context): Boolean {
    return DocumentsContract.isDocumentUri(ctx, this)
}

/* Check whether this uri is a content uri or not.
*  content uri like content://media/external/images/media/1302716
*  */
private fun Uri.isContentUri(): Boolean {
    var ret = false
    val uriSchema = this.scheme
    if ("content".equals(uriSchema, ignoreCase = true)) {
        ret = true
    }
    return ret
}

/* Check whether this uri is a file uri or not.
*  file uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
* */
private fun Uri.isFileUri(): Boolean {
    var ret = false
    val uriSchema = this.scheme
    if ("file".equals(uriSchema, ignoreCase = true)) {
        ret = true
    }
    return ret
}

/* Check whether this document is provided by ExternalStorageProvider. */
private fun isExternalStoreDoc(uriAuthority: String): Boolean {
    var ret = false

    if ("com.android.externalstorage.documents" == uriAuthority) {
        ret = true
    }

    return ret
}

/* Check whether this document is provided by DownloadsProvider. */
private fun isDownloadDoc(uriAuthority: String): Boolean {
    var ret = false

    if ("com.android.providers.downloads.documents" == uriAuthority) {
        ret = true
    }

    return ret
}

/* Check whether this document is provided by MediaProvider. */
private fun isMediaDoc(uriAuthority: String): Boolean {
    var ret = false

    if ("com.android.providers.media.documents" == uriAuthority) {
        ret = true
    }

    return ret
}

/* Check whether this document is provided by google photos. */
private fun isGooglePhotoDoc(uriAuthority: String): Boolean {
    var ret = false

    if ("com.google.android.apps.photos.contentprovider" == uriAuthority) {
        ret = true
    }

    return ret
}

/* Return uri represented document file real local path.*/
private fun Uri.getImageRealPath(
    contentResolver: ContentResolver,
    whereClause: String?
): String {
    var ret = ""

    // Query the uri with condition.
    val cursor = contentResolver.query(this, null, whereClause, null, null)

    if (cursor != null) {
        val moveToFirst = cursor.moveToFirst()
        if (moveToFirst) {

            // Get columns name by uri type.
            var columnName = MediaStore.Images.Media.DATA

            when {
                this === MediaStore.Images.Media.EXTERNAL_CONTENT_URI -> columnName = MediaStore.Images.Media.DATA
                this === MediaStore.Audio.Media.EXTERNAL_CONTENT_URI -> columnName = MediaStore.Audio.Media.DATA
                this === MediaStore.Video.Media.EXTERNAL_CONTENT_URI -> columnName = MediaStore.Video.Media.DATA
            }

            // Get column index.

            // Get column value which is the uri related file local path.

            // Get column index.
            val imageColumnIndex = cursor.getColumnIndex(columnName)

            // Get column value which is the uri related file local path.
            ret = cursor.getString(imageColumnIndex)
        }
    }

    return ret
}

private fun Uri.getPathForMediaDocument(resolver: ContentResolver): String? {
    // Get uri related document id.
    val documentId = DocumentsContract.getDocumentId(this)
    val idArr =
        documentId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    return if (idArr.size == 2) {
        // First item is document type.
        val docType = idArr[0]

        // Second item is document real id.
        val realDocId = idArr[1]

        // Get content uri by document type.
        var mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        if ("image" == docType) {
            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        } else if ("video" == docType) {
            mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        } else if ("audio" == docType) {
            mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

        // Get where clause with real document id.
        val whereClause = MediaStore.Images.Media._ID + " = " + realDocId

        mediaContentUri.getImageRealPath(resolver, whereClause)
    } else {
        null
    }
}
