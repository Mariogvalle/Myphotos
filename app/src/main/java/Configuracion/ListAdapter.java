package Configuracion;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tareas2p.myphotos.R;

import java.util.List;

import Modelos.Photos;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private static final long DOUBLE_CLICK_TIME_DELTA = 300; // Intervalo máximo entre dos toques consecutivos (en milisegundos)
    private long lastClickTime = 0; // Tiempo del último toque
    private List<Photos> datos;
    private LayoutInflater inflater;
    private Context context;
    final ListAdapter.OnItemClickListener listener;
    private OnItemDoubleClickListener doubleClickListener;

    public static int getSelectedItem() {
        return selectedItem;
    }

    public static int selectedItem = -1;

    public interface OnItemClickListener {
        void onItemClick(Photos photos);
    }

    public interface OnItemDoubleClickListener {
        void onItemDoubleClick(Photos photos);
    }

    public ListAdapter(List<Photos> itemList, Context context, ListAdapter.OnItemClickListener listener,OnItemDoubleClickListener doubleClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.datos = itemList;
        this.listener = listener;
        this.doubleClickListener = doubleClickListener;;
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View view = inflater.inflate(R.layout.activity_listrv, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        //
//        Photos item = datos.get(position);
//        holder.descripcion.setText(item.getDescripcion());
//        descriptionTextView.setText(item.getDescripcion());

//        byte[] imageBlob = item.getPhoto();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
//        holder.imageView.setImageBitmap(bitmap);
        //

        holder.bindData(datos.get(position));
        final int currentPosition = position;

        holder.itemView.setSelected(position == selectedItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Actualizar el índice del elemento seleccionado
                selectedItem = currentPosition;

                // Notificar al adaptador de los cambios
                notifyDataSetChanged();

                // Obtener el tiempo actual del sistema
                long clickTime = System.currentTimeMillis();

                // Verificar si el tiempo transcurrido desde el último toque es menor que el intervalo máximo
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    // Doble clic detectado
                    if (doubleClickListener != null) {
                        doubleClickListener.onItemDoubleClick(datos.get(position));
                        // Toast.makeText(context.getApplicationContext(), "Hola  ", Toast.LENGTH_LONG).show();

                    }
                }

                // Actualizar el tiempo del último toque
                lastClickTime = clickTime;
            }
        });

        final GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (doubleClickListener != null) {
                    doubleClickListener.onItemDoubleClick(datos.get(position));
                    //Toast.makeText(context.getApplicationContext(), "Hola  ", Toast.LENGTH_LONG).show();

                    return true;
                }
                return false;
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Toast.makeText(context.getApplicationContext(), "Hola  ", Toast.LENGTH_LONG).show();
                return gestureDetectorCompat.onTouchEvent(event);
            }
        });
    }

    public void setItems(List<Photos> items) {
        datos = items;
    }

    public void setFilteredList(List<Photos> filteredList) {
        this.datos = filteredList;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView id, descripcion;
        LinearLayout item;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView2);
            id = (TextView) itemView.findViewById(R.id.txtId2);
            descripcion = (TextView) itemView.findViewById(R.id.txtDescripcion2);
            //item=(LinearLayout) itemView.findViewById(R.id.item);
        }

        void bindData(final Photos photos) {
            id.setText(photos.getId());
            descripcion.setText(photos.getDescripcion());
            //limpiarSeleccion();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(photos);
                }
            });
        }

//        public void limpiarSeleccion(){
//            item.setBackgroundColor(ContextCompat.getColor(context, R.color.gris));
//        }

    }
}

