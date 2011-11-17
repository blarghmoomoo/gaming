package blargh.warhammer.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenuFragment extends Fragment {

	protected static final String RANGED_WEAPON_TAG = "rangedWeaponTag";
	protected static final String CHARACTER_TAG = "characterTag";
	private DbAdapter dbAdapter;

	public MainMenuFragment(){

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dbAdapter = DbAdapter.Factory.createCharacterDb(this.getActivity(), "warhammer40k", 1);
		dbAdapter.open();
		
		createCharacterButton();
		createRangedWeaponButton();
	}

	private void createCharacterButton() {
		Button characterButton = (Button) getView().findViewById(R.id.characters);
		characterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
				CharacterFragment characterFragment = (CharacterFragment)fragmentManager.findFragmentByTag(CHARACTER_TAG);
				if (characterFragment == null) {
					// Make new fragment to show this selection.
					characterFragment = new CharacterFragment();
				}

				if(!characterFragment.isVisible()){
					// Execute a transaction, replacing any existing fragment
					// with this one inside the frame.
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.apps, characterFragment, CHARACTER_TAG);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}
			}
		});
	}

	private void createRangedWeaponButton() {
		Button rangedWeaponButton = (Button) getView().findViewById(R.id.rangedWeapon);
		rangedWeaponButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
				RangedWeaponFragment rangedWeaponFragment = (RangedWeaponFragment)fragmentManager.findFragmentByTag(RANGED_WEAPON_TAG);
				if (rangedWeaponFragment == null) {
					// Make new fragment to show this selection.
					rangedWeaponFragment = new RangedWeaponFragment();
				}

				if(!rangedWeaponFragment.isVisible()){
					// Execute a transaction, replacing any existing fragment
					// with this one inside the frame.
					FragmentTransaction ft = fragmentManager.beginTransaction();
					ft.replace(R.id.apps, rangedWeaponFragment, RANGED_WEAPON_TAG);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.mainmenu, container, false);
	}


}
