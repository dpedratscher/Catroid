/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2018 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.uiespresso.testsuites.Cat;
import org.catrobat.catroid.uiespresso.testsuites.Level;
import org.catrobat.catroid.uiespresso.util.UiTestUtils;
import org.catrobat.catroid.uiespresso.util.rules.BaseActivityInstrumentationRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SpriteListFragmentNewObjectFromPaintroidTest {

	private String defaultPath = "file:///storage/emulated/0/Pocket%20Paint/Look.png";

	@Rule
	public BaseActivityInstrumentationRule<ProjectActivity> baseActivityTestRule = new
			BaseActivityInstrumentationRule<>(ProjectActivity.class, ProjectActivity.EXTRA_FRAGMENT_POSITION, ProjectActivity.FRAGMENT_SPRITES);

	@Before
	public void setUp() throws Exception {
		UiTestUtils.createNoObjectsProject(SpriteListFragmentNewObjectFromPaintroidTest.class.getSimpleName());
		baseActivityTestRule.launchActivity();
	}

	@After
	public void tearDown() throws IOException {
		baseActivityTestRule.finishActivity();
		UiTestUtils.deleteProjectFromStorage(SpriteListFragmentNewObjectFromPaintroidTest.class.getSimpleName());
	}

	@Category({Cat.AppUi.class, Level.Detailed.class})
	@Test
	public void testAddSpriteDefaultName() {
		InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				baseActivityTestRule.getActivity()
						.onActivityResult(ProjectActivity.SPRITE_POCKET_PAINT, Activity.RESULT_OK,
								new Intent(Intent.ACTION_VIEW).putExtra(Constants.EXTRA_PICTURE_PATH_POCKET_PAINT, defaultPath));
			}
		});

		onView(withText(R.string.default_object_name))
				.check(matches(isDisplayed()));
	}
}
