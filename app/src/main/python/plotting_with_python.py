import pandas as pd
#import mpl_toolkits.mplot3d.axes3d
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import time

def readDataset(address):
    read_file= pd.read_excel(address)
    return (read_file)

def columnName(address):
    df= pd.read_excel(address)
    df_col= []
    for i in range(len(df.columns)):
        df_col.append(df.columns[i]);
    return (df_col)

def BarPlot(address, Folder_path, X_Axis, Y_Axis):
    df= pd.read_excel(address)
    x= df[X_Axis]
    y= df[Y_Axis]
    plt.figure(figsize=(8,4))
    plt.bar(x, y, color = 'dodgerblue',  width = 1, align = 'center', alpha = 0.7)
    plt.xlabel(X_Axis)
    plt.ylabel(Y_Axis)
    pic_name= time.strftime("%H-%M-%S") # Save images with using hours-minute-second
    save_image_path= Folder_path+'/img-'+pic_name+'.png'
    plt.savefig(save_image_path, dpi = 400, quality = 100)
    return (save_image_path)

def histogram(address, Folader_path, X_Axis):
    df= pd.read_excel(address)
    plt.hist(df[X_Axis], bins = 50, histtype='bar', rwidth = 1.0, color = 'dodgerblue', alpha = 0.8)
    plt.xlabel(X_Axis)
    plt.ylabel('frequency')
    pic_name= time.strftime("%H-%M-%S") # Save images with using hours-minute-second
    save_image_path= Folader_path+'/img-'+pic_name+'.png'
    plt.savefig(save_image_path, dpi = 400, quality = 100)
    return (save_image_path)

def PiePlot(address, Folder_path, Slices, Labels):
    df= pd.read_excel(address)
    slices= df[Slices]
    categories= df[Labels]
    plt.pie(slices, labels = categories, startangle = 90,
            # To display the percent value using Python string formatting.
            autopct = '%1.1f%%'
            )
    pic_name= time.strftime("%H-%M-%S") # Save images with using hours-minute-second
    save_image_path= Folder_path+'/img-'+pic_name+'.png'
    plt.savefig(save_image_path, dpi = 400, quality = 100)
    return (save_image_path)

def scatter3D(address, Folder_path, X_Axis, Y_Axis, Z_Axis, ANGLE):
    df= pd.read_excel(address)
    x_axis= df[X_Axis]
    y_axis= df[Y_Axis]
    z_axis= df[Z_Axis]
    fig = plt.figure()
    ax= Axes3D(fig)
    #ax = fig.gca(projection='3d')
    ax.scatter(x_axis, y_axis, z_axis, c=z_axis, cmap='hsv')
    plt.xlabel(X_Axis)
    plt.ylabel(Y_Axis)
    ax.set_zlabel(Z_Axis)
    ax.view_init(30, ANGLE)
    pic_name= time.strftime("%H-%M-%S") # Save images with using hours-minute-second
    save_image_path= Folder_path+'/img-'+pic_name+'.png'
    plt.savefig(save_image_path, dpi = 400, quality = 100)
    return (save_image_path)

def line3D(address, Folder_path, X_Axis, Y_Axis, Z_Axis, ANGLE):
    df= pd.read_excel(address)
    x_axis= df[X_Axis]
    y_axis= df[Y_Axis]
    z_axis= df[Z_Axis]
    fig= plt.figure()
    ax= Axes3D(fig)
    ax.plot(x_axis, y_axis, z_axis)
    plt.xlabel(X_Axis)
    plt.ylabel(Y_Axis)
    ax.set_zlabel(Z_Axis)
    ax.view_init(30, ANGLE)
    pic_name= time.strftime("%H-%M-%S") # Save images with using hours-minute-second
    save_image_path= Folder_path+'/img-'+pic_name+'.png'
    plt.savefig(save_image_path, dpi = 400, quality = 100)
    return (save_image_path)

'''read_file= pd.read_excel(r'E:\Smanna\Matplotlib Project\Dataset (Excel&CSV)\marksheet.xlsx')
read_file.to_csv(r'E:\Smanna\Matplotlib Project\Dataset (Excel&CSV)\marksheet.csv')
df= pd.read_csv(r'E:\Smanna\Matplotlib Project\Dataset (Excel&CSV)\marksheet.csv')
print(df)
x = df['Name']
y = df['Bengali']
plt.figure(figsize=(8,4))
plt.plot(x, y, linewidth = 1)
#plt.set(xlabel='Name', ylabel='Bengali')
plt.xlabel('Name')
plt.ylabel('Bengali')
plt.show()'''