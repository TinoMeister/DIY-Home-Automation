using Microsoft.EntityFrameworkCore;

namespace DIYHomeAutomationAPI.Models
{
    public class SensorDbContext : DbContext
    {
        public SensorDbContext(DbContextOptions<SensorDbContext> options) : base(options)
        {
        }

        public DbSet<Device> Devices { get; set; }
        public DbSet<History> Histories { get; set; }
        public DbSet<Notification> Notifications { get; set; }
        public DbSet<Restriction> Restrictions { get; set; }
        public DbSet<Room> Rooms { get; set; }
        public DbSet<TaskDevice> TaskDevices { get; set; }
        public DbSet<Task> Tasks { get; set; }
        public DbSet<TypeDevice> TypeDevices { get; set; }
        public DbSet<User> Users { get; set; }

    }
}
