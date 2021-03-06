package ru.voronezhtsev.contentprovider.client.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import ru.voronezhtsev.contentprovider.client.R;
import ru.voronezhtsev.contentprovider.client.data.NoteColor;
import ru.voronezhtsev.contentprovider.client.data.PreferencesDAO;

public class PrefsFragment extends PreferenceFragmentCompat {

    public static String TAG = "PrefsFragment";

    private Preference mTextSizePreference;
    private Preference mColorPreference;

    private NotesRepository mNotesRepository;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mNotesRepository = (NotesRepository) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Activity should implements NotesRepository interface", e);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextSizePreference.setSummary(mNotesRepository.getTextSize());
        mColorPreference.setSummary(NoteColor.parseColor(mNotesRepository.getTextColor()).getName());

        mTextSizePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            mTextSizePreference.setSummary(newValue.toString());
            mNotesRepository.saveTextSize(newValue.toString());
            return true;
        });

        mColorPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            mColorPreference.setSummary(NoteColor.parseColor(newValue.toString()).getName());
            mNotesRepository.saveTextColor(newValue.toString());
            return true;
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs, rootKey);
        mTextSizePreference = getPreferenceManager().findPreference(PreferencesDAO.PREFS_TEXT_SIZE);
        mColorPreference = getPreferenceManager().findPreference(PreferencesDAO.PREFS_TEXT_COLOR);
    }
}