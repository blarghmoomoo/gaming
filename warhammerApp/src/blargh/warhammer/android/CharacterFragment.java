package blargh.warhammer.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class CharacterFragment extends Fragment {

	public CharacterFragment(){
	}

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.character, container, false);

		TabHost tabhost = (TabHost) view.findViewById(android.R.id.tabhost);
		tabhost.setup();
		tabhost.addTab(tabhost.newTabSpec("stats").setIndicator("Stats").setContent(R.id.statsLayout));
		tabhost.addTab(tabhost.newTabSpec("skills").setIndicator("Skills").setContent(R.id.skilllayout));
		tabhost.addTab(tabhost.newTabSpec("talents").setIndicator("Talents").setContent(R.id.talentlayout));
		tabhost.addTab(tabhost.newTabSpec("weapon").setIndicator("Weapon").setContent(R.id.weaponlayout));
		tabhost.addTab(tabhost.newTabSpec("armor").setIndicator("Armor").setContent(R.id.armorlayout));
		tabhost.addTab(tabhost.newTabSpec("gear").setIndicator("Gear").setContent(R.id.gearlayout));
		tabhost.setCurrentTabByTag("stats");
		
		return view;
	}
}
