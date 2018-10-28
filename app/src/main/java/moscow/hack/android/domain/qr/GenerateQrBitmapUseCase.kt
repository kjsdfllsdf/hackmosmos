package moscow.hack.android.domain.qr

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import io.reactivex.Single
import moscow.hack.android.data.user.UserManager
import moscow.hack.android.domain.SingleUseCase
import javax.inject.Inject

class GenerateQrBitmapUseCase @Inject constructor(
    private val userManager: UserManager
) : SingleUseCase<Unit, Bitmap> {

    override fun run(param: Unit): Single<Bitmap> =
        Single.fromCallable {
            val writer = QRCodeWriter()
            val userId = userManager.getUserId()
            val matrix = writer.encode(userId, BarcodeFormat.QR_CODE, 512, 512)
            val bitmap = Bitmap.createBitmap(matrix.width, matrix.height, Bitmap.Config.ARGB_4444)
            for (i in 0 until matrix.width) {
                for (j in 0 until matrix.height) {
                    bitmap.setPixel(i, j, if (matrix.get(i, j)) Color.BLACK else Color.TRANSPARENT)
                }
            }
            bitmap
        }
}
