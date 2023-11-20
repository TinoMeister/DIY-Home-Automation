using System.Net.Sockets;
using System.Net;
using Microsoft.EntityFrameworkCore;
using DIYHomeAutomationAPI.Models;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddDbContext<SensorDbContext>(op =>
    op.UseSqlServer(builder.Configuration.GetConnectionString("DbConnection")));

var app = builder.Build();

//Setup local IP
string localIP = LocalIPAddress();

app.Urls.Add("http://" + localIP + ":7064");

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();

app.MapControllers();

app.Run();

// Get local Ip Address
static string LocalIPAddress()
{
    using Socket socket = new(AddressFamily.InterNetwork, SocketType.Dgram, 0);
    socket.Connect("8.8.8.8", 65530);
    if (socket.LocalEndPoint is IPEndPoint endPoint)
    {
        return endPoint.Address.ToString();
    }
    else
    {
        return "127.0.0.1";
    }
}