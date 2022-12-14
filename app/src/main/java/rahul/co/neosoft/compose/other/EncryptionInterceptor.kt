package rahul.co.neosoft.compose.other

import android.util.Base64
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.create
import okio.Buffer
import rahul.co.neosoft.compose.other.Constants.INIT_VECTOR
import rahul.co.neosoft.compose.other.Constants.SECRET_KEY
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class EncryptionInterceptor : Interceptor {

    private val TAG = EncryptionInterceptor::class.java.simpleName
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val oldBody: RequestBody = request.body!!
        val buffer = Buffer()
        oldBody.writeTo(buffer)
        val strOldBody: String = buffer.readUtf8()

        val mediaType: MediaType? = "text/plain; charset=utf-8".toMediaTypeOrNull()
        val strNewBody: String = encrypt(strOldBody)
        val body: RequestBody = create(mediaType, strNewBody)
        request = request.newBuilder().header("Content-Type", body.contentType().toString())
            .header("Content-Length", body.contentLength().toString())
            .method(request.method, body).build()

        return chain.proceed(request)
     }
}

fun encrypt(value: String): String {
    try {
        val iv = IvParameterSpec(INIT_VECTOR.toByteArray(Charsets.UTF_8))
        val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
        val encrypted: ByteArray = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return ""
}

fun decrypt(value: String): String {
    try {
        val iv = IvParameterSpec(INIT_VECTOR.toByteArray(Charsets.UTF_8))
        val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
        val original = cipher.doFinal(Base64.decode(value, Base64.DEFAULT))
        return String(original)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }

    return ""
}