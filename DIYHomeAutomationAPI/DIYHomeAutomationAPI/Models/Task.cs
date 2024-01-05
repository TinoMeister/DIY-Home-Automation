using DIYHomeAutomationAPI.Models.Enums;

namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a Task
    /// </summary>
    public class Task
    {
        /// <summary>
        /// Task's Id
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Task's Name
        /// </summary>
        public string Name { get; set; } = null!;

        /// <summary>
        /// Task's State
        /// </summary>
        public bool State { get; set; }

        /// <summary>
        /// Task's InitHour
        /// </summary>
        public TimeSpan InitHour { get; set; }

        /// <summary>
        /// Task's EndHour
        /// </summary>
        public TimeSpan EndHour { get; set; }

        /// <summary>
        /// Task's Days of Weeks
        /// </summary>
        public string DaysWeek { get; set; } = null!;

        /// <summary>
        /// List of Task Devices
        /// </summary>
        public List<TaskDevice>? TaskDevices { get; set; }
    }
}
