/**
 * 
 */
package me.iamcxa.remindme.cardfragment;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.internal.Card.OnLongCardClickListener;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import me.iamcxa.remindme.CommonUtils;
import me.iamcxa.remindme.R;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.text.style.SuperscriptSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cxa
 *
 */
public class CursorCardAdapter {

	/*******************************/
	/** Class MyCursorCardAdapter **/
	/*******************************/
	public class MyCursorCardAdapter extends CardCursorAdapter {

		public MyCursorCardAdapter(Context context) {
			
		//	super.setCardListView(getCardListView());
			super(context);
			context=getContext();
		}

		@Override
		protected Card getCardFromCursor(Cursor cursor) {
			MyCursorCard card = new MyCursorCard(getContext());
			setCardFromCursor(card, cursor);

			// Create a CardHeader
			CardHeader header = new CardHeader(getContext());

			// Set the header title
			header.setTitle(card.mainHeader);
			header.setPopupMenu(R.menu.editor_activity_actionbar,
					new CardHeader.OnClickCardHeaderPopupMenuListener() {
						@Override
						public void onMenuItemClick(BaseCard card, MenuItem item) {
							Toast.makeText(
									getContext(),
									"Click on card=" + card.getId() + " item="
											+ item.getTitle(),
									Toast.LENGTH_SHORT).show();
						}
					});

			// Add Header to card
			card.addCardHeader(header);

			final MyCardThumbnail thumb = new MyCardThumbnail(getContext());
			thumb.setDrawableResource(card.resourceIdThumb);
			card.addCardThumbnail(thumb);

			// Set onClick listener
			card.addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW,
					new Card.OnLongCardClickListener() {
						@Override
						public boolean onLongClick(Card card, View view) {
							Toast.makeText(
									getContext(),
									"Card id=" + card.getId()
											+ "LONG Click on Content Area",
									Toast.LENGTH_SHORT).show();
							return true;
						}
					});

			// Set a clickListener on ContentArea
			card.addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW,
					new Card.OnCardClickListener() {
						@Override
						public void onClick(Card card, View view) {
							Toast.makeText(
									getContext(),
									"Card id=" + card.getId()
											+ "Click on Content Area",
									Toast.LENGTH_SHORT).show();
						}
					});

			// Set a clickListener on Header Area
			card.addPartialOnClickListener(Card.CLICK_LISTENER_HEADER_VIEW,
					new Card.OnCardClickListener() {
						@Override
						public void onClick(Card card, View view) {
							Toast.makeText(
									getContext(),
									
									"Card id=" + card.getId()
											+ "Click on Header Area",
									Toast.LENGTH_LONG).show();
						}
					});

			return card;
		}

		private void setCardFromCursor(MyCursorCard card, Cursor cursor) {
			// 準備常數
			CommonUtils.debugMsg(0, "prepare data from cursor...");
			boolean NoLocation = cursor
					.isNull(CommonUtils.RemindmeTaskCursor.IndexColumns.LocationName);
			boolean NoExtrainfo = cursor
					.isNull(CommonUtils.RemindmeTaskCursor.IndexColumns.other);
			int CID = cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.KEY_ID);
			String dintence = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.Distance);
			String startTime = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.StartTime);
			String endTime = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.EndTime);
			String endDate = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.EndDate);
			String LocationName = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.LocationName);
			String extraInfo=cursor.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.other);
			long dayLeft = CommonUtils.getDaysLeft(endDate);
			// int dayLeft = Integer.parseInt("" + dayLeftLong);

			// give a ID.
			CommonUtils.debugMsg(0, "set ID...Card id=" + CID);
			card.setId("" + CID);

			// 卡片標題 - first line
			CommonUtils.debugMsg(0, CID + " set Tittle...");
			card.mainHeader = cursor
					.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.Tittle);

			// 時間日期 - sec line
			CommonUtils.debugMsg(0, CID + " set Date/Time...");
			CommonUtils.debugMsg(0, CID + " dayleft=" + dayLeft);
			if ((180 > dayLeft) && (dayLeft > 14)) {
				card.DateTime = "再 " + (int) Math.floor(dayLeft) / 30
						+ " 個月 - " + endDate;
			} else if ((14 > dayLeft) && (dayLeft > 0)) {
				card.DateTime = "再 " + dayLeft + " 天 - " + endDate;
			} else if ((2 > dayLeft) && (dayLeft > 0)) {
				card.DateTime = "再 " + (int) Math.floor(dayLeft * 24)
						+ "小時後 - " + endDate;
			} else if (dayLeft == 0) {
				card.DateTime = "今天 - " + endDate;
			} else {
				card.DateTime = endDate;
			}

			CommonUtils.debugMsg(0, NoLocation+",Location=\"" + LocationName + "\"");
			CommonUtils.debugMsg(0, "dintence=" + dintence);
			// 小圖標顯示 - 判斷是否存有地點資訊
			if (NoLocation==true) {
				card.resourceIdThumb = R.drawable.tear_of_calendar;
				card.LocationName = null;
			} else {
				card.resourceIdThumb = R.drawable.map_marker;
				// 距離與地點資訊
				if (dintence == null) {
					card.LocationName = LocationName;
				} else {
					card.LocationName = "距離 " + dintence + " 公里 - "
							+ LocationName;
				}
			}
			
			CommonUtils.debugMsg(0, "isExtrainfo="+ NoExtrainfo );
			card.Notifications = cursor.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.CalendarID);
			// 可展開額外資訊欄位
			if (NoExtrainfo==false) {
				card.resourceIdThumb = R.drawable.outline_star_act;
				// 額外資訊提示 - 第四行
				card.Notifications = cursor.getString(CommonUtils.RemindmeTaskCursor.IndexColumns.CalendarID);

				// This provides a simple (and useless) expand area
				CardExpand expand = new CardExpand(getContext());
				// Set inner title in Expand Area
				expand.setTitle(cursor.getString(R.string.app_name));
				card.addCardExpand(expand);
			}
			
			// 依照權重給予卡片顏色
			if (cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.PriorityWeight) > 200) {
				card.setBackgroundResourceId(R.drawable.demo_card_selector_color5);
			} else if (cursor
					.getInt(CommonUtils.RemindmeTaskCursor.IndexColumns.PriorityWeight) > 100) {
				card.setBackgroundResourceId(R.drawable.demo_card_selector_color3);
			}

		}


	/*
	 * private void removeCard(Card card) {
	 * 
	 * // Use this code to delete items on DB ContentResolver resolver =
	 * getActivity().getContentResolver(); long noDeleted =
	 * resolver.delete(CommonUtils.CONTENT_URI,
	 * CommonUtils.RemindmeTaskCursor.KeyColumns.KEY_ID + " = ? ", new String[]
	 * { card.getId() });
	 * 
	 * // mAdapter.notifyDataSetChanged();
	 * 
	 * }
	 */

	/***********************/
	/** Class MyThumbnail **/
	/***********************/
	// implment the clickable card thumbnail.
	class MyCardThumbnail extends CardThumbnail {

		public MyCardThumbnail(Context context) {
			super(context);
		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View imageView) {
			ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand
					.builder().highlightView(true).setupView(imageView);
			getParentCard().setViewToClickToExpand(viewToClickToExpand);
		}
	}

	/************************/
	/** Class MyCursorCard **/
	/************************/
	public class MyCursorCard extends Card {

		String DateTime;
		String LocationName;
		String Notifications;
		String mainHeader;

		int resourceIdThumb;

		public MyCursorCard(Context context) {
			super(context, R.layout.card_cursor_inner_content);
		}

		/**********************/
		/**
		 * @param clickListenerContentView
		 *            /** @param onLongCardClickListener
		 **/
		/**********************/
		public void addPartialOnClickListener(int clickListenerContentView,
				OnLongCardClickListener onLongCardClickListener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View view) {
			// Retrieve elements
			TextView mTitleTextView = (TextView) parent
					.findViewById(R.id.card_cursor_main_inner_title);
			TextView mSecondaryTitleTextView = (TextView) parent
					.findViewById(R.id.card_cursor_main_inner_subtitle);
			TextView mThirdTitleTextView = (TextView) parent
					.findViewById(R.id.card_cursor_main_inner_thirdtitle);

			if (mTitleTextView != null)
				mTitleTextView.setText(DateTime);

			if (mSecondaryTitleTextView != null)
				mSecondaryTitleTextView.setText(LocationName);

			if (mThirdTitleTextView != null)
				mThirdTitleTextView.setText(Notifications);

		}
	}

	}}
