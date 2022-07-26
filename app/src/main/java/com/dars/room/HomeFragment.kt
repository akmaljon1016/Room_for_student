package com.dars.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import com.dars.room.databinding.FragmentHomeBinding
import com.dars.room.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: Rec_Adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Rec_Adapter()

        adapter.setOnItemClickListener(object : Rec_Adapter.OnItemClickListener {
            override fun delete(note: Note) {
                GlobalScope.launch(Dispatchers.IO) {
                    deleteNote(note)
                    setList()
                }
            }

            override fun upadate(note: Note) {
                val action = HomeFragmentDirections.actionHomeFragmentToAddFragment(note)
                findNavController().navigate(action)
            }
        })
        GlobalScope.launch(Dispatchers.IO) {
            setList()
        }
        binding.recyclerview.adapter = adapter
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    fun setList() {
        val notes =
            NoteDataBase.DatabaseBuilder.getDatabase(requireContext()).noteDao().getAllNotes()
        adapter.submitList(notes)
    }

    fun deleteNote(note: Note) {
        NoteDataBase.DatabaseBuilder.getDatabase(requireContext()).noteDao().deleteNote(note)
    }
}