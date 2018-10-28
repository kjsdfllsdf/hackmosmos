package moscow.hack.android.presentation.calendar

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.tbruyelle.rxpermissions2.RxPermissions
import moscow.hack.android.R
import moscow.hack.android.domain.model.Event
import moscow.hack.android.presentation.base.BaseFragment
import moscow.hack.android.presentation.calendar.adapter.CalendarItemAdapter
import moscow.hack.android.presentation.calendar.adapter.EventAdapter
import moscow.hack.android.presentation.calendar.model.CalendarItem
import moscow.hack.android.presentation.util.ViewDelegate
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Provider

class CalendarFragment : BaseFragment(), CalendarView {

    companion object {

        fun newInstance(): CalendarFragment = CalendarFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<CalendarPresenter>
    @InjectPresenter
    lateinit var presenter: CalendarPresenter

    private val leftView by ViewDelegate<View>(R.id.left, viewDelegateReset)
    private val rightView by ViewDelegate<View>(R.id.right, viewDelegateReset)
    private val monthView by ViewDelegate<TextView>(R.id.month, viewDelegateReset)
    private val yearView by ViewDelegate<TextView>(R.id.year, viewDelegateReset)
    private val calendarView by ViewDelegate<RecyclerView>(R.id.calendar, viewDelegateReset)
    private val eventView by ViewDelegate<RecyclerView>(R.id.events, viewDelegateReset)
    private val registerView by ViewDelegate<View>(R.id.register, viewDelegateReset)

    private val calendarAdapter = CalendarItemAdapter()
    private val eventAdapter = EventAdapter()
    private lateinit var rxPermissions: RxPermissions

    override val layoutId: Int = R.layout.calendar

    @ProvidePresenter
    fun providePresenter() = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rxPermissions = RxPermissions(this)
        leftView.setOnClickListener { presenter.prevMonth() }
        rightView.setOnClickListener { presenter.nextMonth() }
        calendarAdapter.calendarClick = { presenter.selectDay(it) }
        calendarView.adapter = calendarAdapter
        eventAdapter.eventClick = { presenter.onEventClick(it) }
        eventView.adapter = eventAdapter
        registerView.setOnClickListener { presenter.attend() }
    }

    override fun showData(items: List<CalendarItem>, monthYear: Long) {
        monthView.text = SimpleDateFormat("MMMM").format(monthYear)
        yearView.text = SimpleDateFormat("yyyy").format(monthYear)
        calendarAdapter.submitList(items)
    }

    override fun showEvents(items: List<Event>) {
        eventAdapter.submitList(items)
    }

    override fun showLoading(loading: Boolean) {
        // TODO
    }

    override fun showError(text: CharSequence) {
        Toast.makeText(context!!, text.toString(), Toast.LENGTH_LONG).show()
    }

    override fun showRegisterButton(show: Boolean) {
        registerView.visibility = if (show) View.VISIBLE else View.GONE
    }

    @SuppressLint("CheckResult")
    override fun checkCamera(eventId: String) {
        rxPermissions.request(Manifest.permission.CAMERA)
            .subscribe({
                if (!it) {
                    throw IllegalStateException()
                }
                presenter.onCameraPermissionGranted(eventId)
            }, {
                Toast.makeText(context!!, "Нужна камера", Toast.LENGTH_LONG).show()
            })
    }

    override fun showDialog(eventId: String) {
        val dialog = AlertDialog.Builder(context!!)
            .setTitle("Записаться на занятие?")
            .setMessage("Тем самым вы сообщите тренеру, что придете на занятие.")
            .setPositiveButton("Записаться") { dialog, which -> presenter.register(eventId) }
            .setNegativeButton("Отмена") { dialog, which -> dialog.dismiss() }
        dialog.show()
    }
}
