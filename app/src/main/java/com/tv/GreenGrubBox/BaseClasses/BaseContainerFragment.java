package com.tv.GreenGrubBox.BaseClasses;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.utils.Logger;

import java.util.List;

public class BaseContainerFragment extends BaseFragment {

	private static final String TAG = BaseContainerFragment.class.getSimpleName();

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		//System.out.println("base Container OnActivityResult");
		List<Fragment> fragments = getChildFragmentManager().getFragments();
		if (fragments != null) {
			for (Fragment fragment : fragments) {
				fragment.onActivityResult(requestCode, resultCode, data);
			}
		}

	}

	FragmentTransaction transaction = null;

	public void replaceFragment(Fragment fragment, boolean addToBackStack) {

		FragmentTransaction ft = getChildFragmentManager().beginTransaction();

		ft.setCustomAnimations(0, R.anim.anim_slide_in_right, R.anim.stay, R.anim.anim_slide_out_right);


		ft.add(R.id.container_framelayout, fragment);
		if (addToBackStack){
			ft.addToBackStack(null);
		}
		ft.commitAllowingStateLoss();
		getChildFragmentManager().executePendingTransactions();



	}

	public boolean popFragment() {

		Logger.logsError(TAG, "pop fragment: " + getChildFragmentManager().getBackStackEntryCount());


		boolean isPop = false;



		if (getChildFragmentManager().getBackStackEntryCount() > 0)
		{
			isPop = true;
			Fragment fragment = getChildFragmentManager().getFragments()
					.get(getChildFragmentManager().getBackStackEntryCount() - 1);
			fragment.onResume();
			getChildFragmentManager().popBackStack();
//			getChildFragmentManager().popBackStackImmediate();
			getChildFragmentManager().beginTransaction().
					remove(getChildFragmentManager().findFragmentById(R.id.container_framelayout)).commit();

		} else{
			getActivity().finish();
		}
		return isPop;
	}

	@Override
	protected void setUp(View view) {

	}
}
