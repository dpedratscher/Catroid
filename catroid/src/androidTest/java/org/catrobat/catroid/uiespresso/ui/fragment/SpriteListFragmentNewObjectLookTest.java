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
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.uiespresso.testsuites.Cat;
import org.catrobat.catroid.uiespresso.testsuites.Level;
import org.catrobat.catroid.uiespresso.util.FileTestUtils;
import org.catrobat.catroid.uiespresso.util.UiTestUtils;
import org.catrobat.catroid.uiespresso.util.rules.BaseActivityInstrumentationRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.catrobat.catroid.uiespresso.ui.fragment.rvutils.RecyclerViewInteractionWrapper.onRecyclerView;

@RunWith(AndroidJUnit4.class)
public class SpriteListFragmentNewObjectLookTest {

	private File tmpTestFile;
	private String tmpTestFileName;

	@Rule
	public BaseActivityInstrumentationRule<ProjectActivity> baseActivityTestRule = new
			BaseActivityInstrumentationRule<>(ProjectActivity.class, ProjectActivity.EXTRA_FRAGMENT_POSITION, ProjectActivity.FRAGMENT_SPRITES);

	@Before
	public void setUp() throws Exception {
		UiTestUtils.createNoObjectsProject(SpriteListFragmentNewObjectLookTest.class.getSimpleName());
		createTmpTestFile();
		baseActivityTestRule.launchActivity();
	}

	@After
	public void tearDown() throws IOException {
		baseActivityTestRule.finishActivity();
		UiTestUtils.deleteProjectFromStorage(SpriteListFragmentNewObjectLookTest.class.getSimpleName());
	}

	@Category({Cat.AppUi.class, Level.Detailed.class})
	@Test
	public void testAddObjectInnerLookDefaultNamePocketPaint() {
		InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				baseActivityTestRule.getActivity()
						.onActivityResult(ProjectActivity.SPRITE_POCKET_PAINT, Activity.RESULT_OK,
								new Intent(Intent.ACTION_VIEW).putExtra(Constants.EXTRA_PICTURE_PATH_POCKET_PAINT, tmpTestFile.getAbsolutePath()));
			}
		});

		clickOK();
		onView(withText(R.string.default_object_name))
				.perform(click());
		onView(withText(R.string.looks))
				.perform(click());

		onRecyclerView().atPosition(0).onChildView(R.id.title_view)
				.check(matches(withText(R.string.default_look_name)));
	}

	@Category({Cat.AppUi.class, Level.Detailed.class})
	@Test
	public void testAddObjectInnerLookDefaultNameCamera() {
		InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				baseActivityTestRule.getActivity()
						.onActivityResult(ProjectActivity.SPRITE_CAMERA, Activity.RESULT_OK, null);
			}
		});

		clickOK();
		onView(withText(R.string.default_object_name))
				.perform(click());
		onView(withText(R.string.looks))
				.perform(click());

		onRecyclerView().atPosition(0).onChildView(R.id.title_view)
				.check(matches(withText(UiTestUtils.getResourcesString(R.string.default_look_name))));
	}

	@Category({Cat.AppUi.class, Level.Detailed.class})
	@Test
	public void testAddObjectInnerLookDefaultNameMediaLibrary() {
		InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				baseActivityTestRule.getActivity()
						.onActivityResult(ProjectActivity.SPRITE_LIBRARY, Activity.RESULT_OK,
								new Intent(Intent.ACTION_VIEW).putExtra(WebViewActivity.MEDIA_FILE_PATH, tmpTestFileName));
			}
		});

		clickOK();
		onView(withText(tmpTestFileName))
				.perform(click());
		onView(withText(R.string.looks))
				.perform(click());

		onRecyclerView().atPosition(0).onChildView(R.id.title_view)
				.check(matches(withText(tmpTestFileName)));
	}

	@Category({Cat.AppUi.class, Level.Detailed.class})
	@Test
	public void testAddObjectInnerLookDefaultNameSelection() {
		InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				baseActivityTestRule.getActivity()
						.onActivityResult(ProjectActivity.SPRITE_FILE, Activity.RESULT_OK,
								new Intent(Intent.ACTION_VIEW, Uri.fromFile(tmpTestFile)));
			}
		});

		clickOK();
		onView(withText(tmpTestFileName))
				.perform(click());
		onView(withText(R.string.looks))
				.perform(click());

		onRecyclerView().atPosition(0).onChildView(R.id.title_view)
				.check(matches(withText(tmpTestFileName)));
	}

	private void createTmpTestFile() throws IOException{
		tmpTestFile = File.createTempFile("Test", ".png", null);
		tmpTestFileName = FileTestUtils.filenameWithoutExtension(tmpTestFile.getName());
	}

	private void clickOK() {
		onView(withText(R.string.ok))
				.perform(click());
	}
}
